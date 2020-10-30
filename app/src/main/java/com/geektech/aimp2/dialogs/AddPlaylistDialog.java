package com.geektech.aimp2.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.geektech.aimp2.MusicPlayer;
import com.geektech.aimp2.dataloaders.PlaylistLoader;
import com.geektech.aimp2.models.Playlist;
import com.geektech.aimp2.models.Song;
import com.geektech.aimp2.R;

import java.util.List;

/**
 * Created by naman on 20/12/15.
 */
public class AddPlaylistDialog extends DialogFragment {

    public static AddPlaylistDialog newInstance(Song song) {
        long[] songs = new long[1];
        songs[0] = song.id;
        return newInstance(songs);
    }

    public static AddPlaylistDialog newInstance(long[] songList) {
        AddPlaylistDialog dialog = new AddPlaylistDialog();
        Bundle bundle = new Bundle();
        bundle.putLongArray("songs", songList);
        dialog.setArguments(bundle);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final List<Playlist> playlists = PlaylistLoader.getPlaylists(getActivity(), false);
        CharSequence[] chars = new CharSequence[playlists.size() + 1];
        chars[0] = getString(R.string.create_new_playlist);

        for (int i = 0; i < playlists.size(); i++) {
            chars[i + 1] = playlists.get(i).name;
        }
        return new MaterialDialog.Builder(getActivity()).title(R.string.add_to_playlist).items(chars).itemsCallback(new MaterialDialog.ListCallback() {
            @Override
            public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                long[] songs = getArguments().getLongArray("songs");
                if (which == 0) {
                    CreatePlaylistDialog.newInstance(songs).show(getActivity().getSupportFragmentManager(), "CREATE_PLAYLIST");
                    return;
                }

                MusicPlayer.addToPlaylist(getActivity(), songs, playlists.get(which - 1).id);
                dialog.dismiss();

            }
        }).build();
    }
}
